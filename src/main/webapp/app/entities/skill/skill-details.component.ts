import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { ISkill } from '@/shared/model/skill.model';
import SkillService from './skill.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class SkillDetails extends mixins(JhiDataUtils) {
  @Inject('skillService') private skillService: () => SkillService;
  @Inject('alertService') private alertService: () => AlertService;

  public skill: ISkill = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.skillId) {
        vm.retrieveSkill(to.params.skillId);
      }
    });
  }

  public retrieveSkill(skillId) {
    this.skillService()
      .find(skillId)
      .then(res => {
        this.skill = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
