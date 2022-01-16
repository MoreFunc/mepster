import { Component, Vue, Inject } from 'vue-property-decorator';

import { IMonthlyAvailability } from '@/shared/model/monthly-availability.model';
import MonthlyAvailabilityService from './monthly-availability.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class MonthlyAvailabilityDetails extends Vue {
  @Inject('monthlyAvailabilityService') private monthlyAvailabilityService: () => MonthlyAvailabilityService;
  @Inject('alertService') private alertService: () => AlertService;

  public monthlyAvailability: IMonthlyAvailability = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.monthlyAvailabilityId) {
        vm.retrieveMonthlyAvailability(to.params.monthlyAvailabilityId);
      }
    });
  }

  public retrieveMonthlyAvailability(monthlyAvailabilityId) {
    this.monthlyAvailabilityService()
      .find(monthlyAvailabilityId)
      .then(res => {
        this.monthlyAvailability = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
