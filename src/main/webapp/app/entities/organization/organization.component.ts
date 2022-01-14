import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IOrganization } from '@/shared/model/organization.model';

import OrganizationService from './organization.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Organization extends Vue {
  @Inject('organizationService') private organizationService: () => OrganizationService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public organizations: IOrganization[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllOrganizations();
  }

  public clear(): void {
    this.retrieveAllOrganizations();
  }

  public retrieveAllOrganizations(): void {
    this.isFetching = true;
    this.organizationService()
      .retrieve()
      .then(
        res => {
          this.organizations = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
          this.alertService().showHttpError(this, err.response);
        }
      );
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: IOrganization): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeOrganization(): void {
    this.organizationService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('mepsterApp.organization.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllOrganizations();
        this.closeDialog();
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
