/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import OrganizationComponent from '@/entities/organization/organization.vue';
import OrganizationClass from '@/entities/organization/organization.component';
import OrganizationService from '@/entities/organization/organization.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(ToastPlugin);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('Organization Management Component', () => {
    let wrapper: Wrapper<OrganizationClass>;
    let comp: OrganizationClass;
    let organizationServiceStub: SinonStubbedInstance<OrganizationService>;

    beforeEach(() => {
      organizationServiceStub = sinon.createStubInstance<OrganizationService>(OrganizationService);
      organizationServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<OrganizationClass>(OrganizationComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          organizationService: () => organizationServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      organizationServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllOrganizations();
      await comp.$nextTick();

      // THEN
      expect(organizationServiceStub.retrieve.called).toBeTruthy();
      expect(comp.organizations[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      organizationServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(organizationServiceStub.retrieve.callCount).toEqual(1);

      comp.removeOrganization();
      await comp.$nextTick();

      // THEN
      expect(organizationServiceStub.delete.called).toBeTruthy();
      expect(organizationServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
