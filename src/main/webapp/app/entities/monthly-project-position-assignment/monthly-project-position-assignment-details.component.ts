import { Component, Vue, Inject } from 'vue-property-decorator';

import { IMonthlyProjectPositionAssignment } from '@/shared/model/monthly-project-position-assignment.model';
import MonthlyProjectPositionAssignmentService from './monthly-project-position-assignment.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class MonthlyProjectPositionAssignmentDetails extends Vue {
  @Inject('monthlyProjectPositionAssignmentService')
  private monthlyProjectPositionAssignmentService: () => MonthlyProjectPositionAssignmentService;
  @Inject('alertService') private alertService: () => AlertService;

  public monthlyProjectPositionAssignment: IMonthlyProjectPositionAssignment = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.monthlyProjectPositionAssignmentId) {
        vm.retrieveMonthlyProjectPositionAssignment(to.params.monthlyProjectPositionAssignmentId);
      }
    });
  }

  public retrieveMonthlyProjectPositionAssignment(monthlyProjectPositionAssignmentId) {
    this.monthlyProjectPositionAssignmentService()
      .find(monthlyProjectPositionAssignmentId)
      .then(res => {
        this.monthlyProjectPositionAssignment = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
