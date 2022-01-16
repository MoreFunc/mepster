import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, minValue, maxValue, required } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import PersonService from '@/entities/person/person.service';
import { IPerson } from '@/shared/model/person.model';

import { IMonthlyAvailability, MonthlyAvailability } from '@/shared/model/monthly-availability.model';
import MonthlyAvailabilityService from './monthly-availability.service';

const validations: any = {
  monthlyAvailability: {
    yearmonth: {},
    percent: {
      numeric,
      min: minValue(0),
      max: maxValue(100),
    },
    isActive: {
      required,
    },
    person: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class MonthlyAvailabilityUpdate extends Vue {
  @Inject('monthlyAvailabilityService') private monthlyAvailabilityService: () => MonthlyAvailabilityService;
  @Inject('alertService') private alertService: () => AlertService;

  public monthlyAvailability: IMonthlyAvailability = new MonthlyAvailability();

  @Inject('personService') private personService: () => PersonService;

  public people: IPerson[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.monthlyAvailabilityId) {
        vm.retrieveMonthlyAvailability(to.params.monthlyAvailabilityId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.monthlyAvailability.id) {
      this.monthlyAvailabilityService()
        .update(this.monthlyAvailability)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('mepsterApp.monthlyAvailability.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    } else {
      this.monthlyAvailabilityService()
        .create(this.monthlyAvailability)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('mepsterApp.monthlyAvailability.created', { param: param.id });
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    }
  }

  public retrieveMonthlyAvailability(monthlyAvailabilityId): void {
    this.monthlyAvailabilityService()
      .find(monthlyAvailabilityId)
      .then(res => {
        this.monthlyAvailability = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.personService()
      .retrieve()
      .then(res => {
        this.people = res.data;
      });
  }
}
