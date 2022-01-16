import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IMonthlyAvailability } from '@/shared/model/monthly-availability.model';

import MonthlyAvailabilityService from './monthly-availability.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class MonthlyAvailability extends Vue {
  @Inject('monthlyAvailabilityService') private monthlyAvailabilityService: () => MonthlyAvailabilityService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public monthlyAvailabilities: IMonthlyAvailability[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllMonthlyAvailabilitys();
  }

  public clear(): void {
    this.retrieveAllMonthlyAvailabilitys();
  }

  public retrieveAllMonthlyAvailabilitys(): void {
    this.isFetching = true;
    this.monthlyAvailabilityService()
      .retrieve()
      .then(
        res => {
          this.monthlyAvailabilities = res.data;
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

  public prepareRemove(instance: IMonthlyAvailability): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeMonthlyAvailability(): void {
    this.monthlyAvailabilityService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('mepsterApp.monthlyAvailability.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllMonthlyAvailabilitys();
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
