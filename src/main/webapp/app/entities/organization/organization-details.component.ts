import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { IOrganization } from '@/shared/model/organization.model';
import OrganizationService from './organization.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class OrganizationDetails extends mixins(JhiDataUtils) {
  @Inject('organizationService') private organizationService: () => OrganizationService;
  @Inject('alertService') private alertService: () => AlertService;

  public organization: IOrganization = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.organizationId) {
        vm.retrieveOrganization(to.params.organizationId);
      }
    });
  }

  public retrieveOrganization(organizationId) {
    this.organizationService()
      .find(organizationId)
      .then(res => {
        this.organization = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
