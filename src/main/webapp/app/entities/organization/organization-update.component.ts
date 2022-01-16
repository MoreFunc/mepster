import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { required, minLength, maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import PersonService from '@/entities/person/person.service';
import { IPerson } from '@/shared/model/person.model';

import ProjectService from '@/entities/project/project.service';
import { IProject } from '@/shared/model/project.model';

import { IOrganization, Organization } from '@/shared/model/organization.model';
import OrganizationService from './organization.service';

const validations: any = {
  organization: {
    name: {
      required,
      minLength: minLength(2),
      maxLength: maxLength(50),
    },
    street: {
      maxLength: maxLength(50),
    },
    number: {
      maxLength: maxLength(5),
    },
    city: {
      maxLength: maxLength(20),
    },
    zipcode: {
      maxLength: maxLength(5),
    },
    country: {
      maxLength: maxLength(20),
    },
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
export default class OrganizationUpdate extends mixins(JhiDataUtils) {
  @Inject('organizationService') private organizationService: () => OrganizationService;
  @Inject('alertService') private alertService: () => AlertService;

  public organization: IOrganization = new Organization();

  @Inject('personService') private personService: () => PersonService;

  public people: IPerson[] = [];

  @Inject('projectService') private projectService: () => ProjectService;

  public projects: IProject[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.organizationId) {
        vm.retrieveOrganization(to.params.organizationId);
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
    if (this.organization.id) {
      this.organizationService()
        .update(this.organization)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('mepsterApp.organization.updated', { param: param.id });
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
      this.organizationService()
        .create(this.organization)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('mepsterApp.organization.created', { param: param.id });
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

  public retrieveOrganization(organizationId): void {
    this.organizationService()
      .find(organizationId)
      .then(res => {
        this.organization = res;
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
    this.projectService()
      .retrieve()
      .then(res => {
        this.projects = res.data;
      });
  }
}
