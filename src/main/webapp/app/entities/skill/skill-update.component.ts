import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { required, minLength, maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import ProjectPositionService from '@/entities/project-position/project-position.service';
import { IProjectPosition } from '@/shared/model/project-position.model';

import PersonService from '@/entities/person/person.service';
import { IPerson } from '@/shared/model/person.model';

import { ISkill, Skill } from '@/shared/model/skill.model';
import SkillService from './skill.service';

const validations: any = {
  skill: {
    title: {
      required,
      minLength: minLength(2),
      maxLength: maxLength(30),
    },
    description: {},
  },
};

@Component({
  validations,
})
export default class SkillUpdate extends mixins(JhiDataUtils) {
  @Inject('skillService') private skillService: () => SkillService;
  @Inject('alertService') private alertService: () => AlertService;

  public skill: ISkill = new Skill();

  @Inject('projectPositionService') private projectPositionService: () => ProjectPositionService;

  public projectPositions: IProjectPosition[] = [];

  @Inject('personService') private personService: () => PersonService;

  public people: IPerson[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.skillId) {
        vm.retrieveSkill(to.params.skillId);
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
    if (this.skill.id) {
      this.skillService()
        .update(this.skill)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('mepsterApp.skill.updated', { param: param.id });
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
      this.skillService()
        .create(this.skill)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('mepsterApp.skill.created', { param: param.id });
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

  public retrieveSkill(skillId): void {
    this.skillService()
      .find(skillId)
      .then(res => {
        this.skill = res;
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
