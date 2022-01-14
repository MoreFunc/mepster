import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IMonthlyProjectPositionAssignment } from '@/shared/model/monthly-project-position-assignment.model';

import MonthlyProjectPositionAssignmentService from './monthly-project-position-assignment.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class MonthlyProjectPositionAssignment extends Vue {
  @Inject('monthlyProjectPositionAssignmentService')
  private monthlyProjectPositionAssignmentService: () => MonthlyProjectPositionAssignmentService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public monthlyProjectPositionAssignments: IMonthlyProjectPositionAssignment[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllMonthlyProjectPositionAssignments();
  }

  public clear(): void {
    this.retrieveAllMonthlyProjectPositionAssignments();
  }

  public retrieveAllMonthlyProjectPositionAssignments(): void {
    this.isFetching = true;
    this.monthlyProjectPositionAssignmentService()
      .retrieve()
      .then(
        res => {
          this.monthlyProjectPositionAssignments = res.data;
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

  public prepareRemove(instance: IMonthlyProjectPositionAssignment): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeMonthlyProjectPositionAssignment(): void {
    this.monthlyProjectPositionAssignmentService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('mepsterApp.monthlyProjectPositionAssignment.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllMonthlyProjectPositionAssignments();
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
