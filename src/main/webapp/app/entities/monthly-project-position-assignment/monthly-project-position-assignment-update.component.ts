import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, minValue, maxValue, required } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import ProjectPositionService from '@/entities/project-position/project-position.service';
import { IProjectPosition } from '@/shared/model/project-position.model';

import PersonService from '@/entities/person/person.service';
import { IPerson } from '@/shared/model/person.model';

import {
  IMonthlyProjectPositionAssignment,
  MonthlyProjectPositionAssignment,
} from '@/shared/model/monthly-project-position-assignment.model';
import MonthlyProjectPositionAssignmentService from './monthly-project-position-assignment.service';

const validations: any = {
  monthlyProjectPositionAssignment: {
    yearmonth: {},
    percent: {
      numeric,
      min: minValue(0),
      max: maxValue(100),
    },
    active: {
      required,
    },
    projectPosition: {
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
export default class MonthlyProjectPositionAssignmentUpdate extends Vue {
  @Inject('monthlyProjectPositionAssignmentService')
  private monthlyProjectPositionAssignmentService: () => MonthlyProjectPositionAssignmentService;
  @Inject('alertService') private alertService: () => AlertService;

  public monthlyProjectPositionAssignment: IMonthlyProjectPositionAssignment = new MonthlyProjectPositionAssignment();

  @Inject('projectPositionService') private projectPositionService: () => ProjectPositionService;

  public projectPositions: IProjectPosition[] = [];

  @Inject('personService') private personService: () => PersonService;

  public people: IPerson[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.monthlyProjectPositionAssignmentId) {
        vm.retrieveMonthlyProjectPositionAssignment(to.params.monthlyProjectPositionAssignmentId);
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
    if (this.monthlyProjectPositionAssignment.id) {
      this.monthlyProjectPositionAssignmentService()
        .update(this.monthlyProjectPositionAssignment)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('mepsterApp.monthlyProjectPositionAssignment.updated', { param: param.id });
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
      this.monthlyProjectPositionAssignmentService()
        .create(this.monthlyProjectPositionAssignment)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('mepsterApp.monthlyProjectPositionAssignment.created', { param: param.id });
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

  public retrieveMonthlyProjectPositionAssignment(monthlyProjectPositionAssignmentId): void {
    this.monthlyProjectPositionAssignmentService()
      .find(monthlyProjectPositionAssignmentId)
      .then(res => {
        this.monthlyProjectPositionAssignment = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.projectPositionService()
      .retrieve()
      .then(res => {
        this.projectPositions = res.data;
      });
    this.personService()
      .retrieve()
      .then(res => {
        this.people = res.data;
      });
  }
}
