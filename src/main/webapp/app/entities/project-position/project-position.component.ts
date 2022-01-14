import { mixins } from 'vue-class-component';
import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IProjectPosition } from '@/shared/model/project-position.model';

import JhiDataUtils from '@/shared/data/data-utils.service';

import ProjectPositionService from './project-position.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class ProjectPosition extends mixins(JhiDataUtils) {
  @Inject('projectPositionService') private projectPositionService: () => ProjectPositionService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public projectPositions: IProjectPosition[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllProjectPositions();
  }

  public clear(): void {
    this.retrieveAllProjectPositions();
  }

  public retrieveAllProjectPositions(): void {
    this.isFetching = true;
    this.projectPositionService()
      .retrieve()
      .then(
        res => {
          this.projectPositions = res.data;
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

  public prepareRemove(instance: IProjectPosition): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeProjectPosition(): void {
    this.projectPositionService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('mepsterApp.projectPosition.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllProjectPositions();
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
