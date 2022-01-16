import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { required, minLength, maxLength, numeric, minValue, maxValue } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import RoleService from '@/entities/role/role.service';
import { IRole } from '@/shared/model/role.model';

import SkillService from '@/entities/skill/skill.service';
import { ISkill } from '@/shared/model/skill.model';

import ProjectService from '@/entities/project/project.service';
import { IProject } from '@/shared/model/project.model';

import MonthlyProjectPositionAssignmentService from '@/entities/monthly-project-position-assignment/monthly-project-position-assignment.service';
import { IMonthlyProjectPositionAssignment } from '@/shared/model/monthly-project-position-assignment.model';

import { IProjectPosition, ProjectPosition } from '@/shared/model/project-position.model';
import ProjectPositionService from './project-position.service';

const validations: any = {
  projectPosition: {
    title: {
      required,
      minLength: minLength(2),
      maxLength: maxLength(20),
    },
    description: {},
    startDate: {},
    endDate: {},
    percent: {
      numeric,
      min: minValue(0),
      max: maxValue(100),
    },
    role: {
      required,
    },
    project: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class ProjectPositionUpdate extends mixins(JhiDataUtils) {
  @Inject('projectPositionService') private projectPositionService: () => ProjectPositionService;
  @Inject('alertService') private alertService: () => AlertService;

  public projectPosition: IProjectPosition = new ProjectPosition();

  @Inject('roleService') private roleService: () => RoleService;

  public roles: IRole[] = [];

  @Inject('skillService') private skillService: () => SkillService;

  public skills: ISkill[] = [];

  @Inject('projectService') private projectService: () => ProjectService;

  public projects: IProject[] = [];

  @Inject('monthlyProjectPositionAssignmentService')
  private monthlyProjectPositionAssignmentService: () => MonthlyProjectPositionAssignmentService;

  public monthlyProjectPositionAssignments: IMonthlyProjectPositionAssignment[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.projectPositionId) {
        vm.retrieveProjectPosition(to.params.projectPositionId);
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
    if (this.projectPosition.id) {
      this.projectPositionService()
        .update(this.projectPosition)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('mepsterApp.projectPosition.updated', { param: param.id });
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
      this.projectPositionService()
        .create(this.projectPosition)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('mepsterApp.projectPosition.created', { param: param.id });
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

  public retrieveProjectPosition(projectPositionId): void {
    this.projectPositionService()
      .find(projectPositionId)
      .then(res => {
        this.projectPosition = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.roleService()
      .retrieve()
      .then(res => {
        this.roles = res.data;
      });
    this.skillService()
      .retrieve()
      .then(res => {
        this.skills = res.data;
      });
    this.projectService()
      .retrieve()
      .then(res => {
        this.projects = res.data;
      });
    this.monthlyProjectPositionAssignmentService()
      .retrieve()
      .then(res => {
        this.monthlyProjectPositionAssignments = res.data;
      });
  }
}
