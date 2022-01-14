import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { IProjectPosition } from '@/shared/model/project-position.model';
import ProjectPositionService from './project-position.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class ProjectPositionDetails extends mixins(JhiDataUtils) {
  @Inject('projectPositionService') private projectPositionService: () => ProjectPositionService;
  @Inject('alertService') private alertService: () => AlertService;

  public projectPosition: IProjectPosition = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.projectPositionId) {
        vm.retrieveProjectPosition(to.params.projectPositionId);
      }
    });
  }

  public retrieveProjectPosition(projectPositionId) {
    this.projectPositionService()
      .find(projectPositionId)
      .then(res => {
        this.projectPosition = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
