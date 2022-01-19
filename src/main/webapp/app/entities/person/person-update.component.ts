import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { required, minLength, maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import SkillService from '@/entities/skill/skill.service';
import { ISkill } from '@/shared/model/skill.model';

import RoleService from '@/entities/role/role.service';
import { IRole } from '@/shared/model/role.model';

import OrganizationService from '@/entities/organization/organization.service';
import { IOrganization } from '@/shared/model/organization.model';

import MonthlyProjectPositionAssignmentService from '@/entities/monthly-project-position-assignment/monthly-project-position-assignment.service';
import { IMonthlyProjectPositionAssignment } from '@/shared/model/monthly-project-position-assignment.model';

import MonthlyAvailabilityService from '@/entities/monthly-availability/monthly-availability.service';
import { IMonthlyAvailability } from '@/shared/model/monthly-availability.model';

import { IPerson, Person } from '@/shared/model/person.model';
import PersonService from './person.service';

const validations: any = {
  person: {
    firstname: {
      required,
      minLength: minLength(2),
      maxLength: maxLength(50),
    },
    lastname: {
      required,
      minLength: minLength(2),
      maxLength: maxLength(50),
    },
    type: {},
    lead: {},
    phoneNumber: {
      maxLength: maxLength(30),
    },
    email: {
      maxLength: maxLength(30),
    },
    notes: {},
  },
};

@Component({
  validations,
})
export default class PersonUpdate extends mixins(JhiDataUtils) {
  @Inject('personService') private personService: () => PersonService;
  @Inject('alertService') private alertService: () => AlertService;

  public person: IPerson = new Person();

  @Inject('skillService') private skillService: () => SkillService;

  public skills: ISkill[] = [];

  @Inject('roleService') private roleService: () => RoleService;

  public roles: IRole[] = [];

  @Inject('organizationService') private organizationService: () => OrganizationService;

  public organizations: IOrganization[] = [];

  @Inject('monthlyProjectPositionAssignmentService')
  private monthlyProjectPositionAssignmentService: () => MonthlyProjectPositionAssignmentService;

  public monthlyProjectPositionAssignments: IMonthlyProjectPositionAssignment[] = [];

  @Inject('monthlyAvailabilityService') private monthlyAvailabilityService: () => MonthlyAvailabilityService;

  public monthlyAvailabilities: IMonthlyAvailability[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.personId) {
        vm.retrievePerson(to.params.personId);
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
    this.person.skills = [];
    this.person.roles = [];
  }

  public save(): void {
    this.isSaving = true;
    if (this.person.id) {
      this.personService()
        .update(this.person)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('mepsterApp.person.updated', { param: param.id });
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
      this.personService()
        .create(this.person)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('mepsterApp.person.created', { param: param.id });
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

  public retrievePerson(personId): void {
    this.personService()
      .find(personId)
      .then(res => {
        this.person = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.skillService()
      .retrieve()
      .then(res => {
        this.skills = res.data;
      });
    this.roleService()
      .retrieve()
      .then(res => {
        this.roles = res.data;
      });
    this.organizationService()
      .retrieve()
      .then(res => {
        this.organizations = res.data;
      });
    this.monthlyProjectPositionAssignmentService()
      .retrieve()
      .then(res => {
        this.monthlyProjectPositionAssignments = res.data;
      });
    this.monthlyAvailabilityService()
      .retrieve()
      .then(res => {
        this.monthlyAvailabilities = res.data;
      });
  }

  public getSelected(selectedVals, option): any {
    if (selectedVals) {
      return selectedVals.find(value => option.id === value.id) ?? option;
    }
    return option;
  }
}
