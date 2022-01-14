/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import OrganizationUpdateComponent from '@/entities/organization/organization-update.vue';
import OrganizationClass from '@/entities/organization/organization-update.component';
import OrganizationService from '@/entities/organization/organization.service';

import PersonService from '@/entities/person/person.service';

import ProjectService from '@/entities/project/project.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.use(ToastPlugin);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('Organization Management Update Component', () => {
    let wrapper: Wrapper<OrganizationClass>;
    let comp: OrganizationClass;
    let organizationServiceStub: SinonStubbedInstance<OrganizationService>;

    beforeEach(() => {
      organizationServiceStub = sinon.createStubInstance<OrganizationService>(OrganizationService);

      wrapper = shallowMount<OrganizationClass>(OrganizationUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          organizationService: () => organizationServiceStub,
          alertService: () => new AlertService(),

          personService: () =>
            sinon.createStubInstance<PersonService>(PersonService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          projectService: () =>
            sinon.createStubInstance<ProjectService>(ProjectService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.organization = entity;
        organizationServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(organizationServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.organization = entity;
        organizationServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(organizationServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundOrganization = { id: 123 };
        organizationServiceStub.find.resolves(foundOrganization);
        organizationServiceStub.retrieve.resolves([foundOrganization]);

        // WHEN
        comp.beforeRouteEnter({ params: { organizationId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.organization).toBe(foundOrganization);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
