/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import MonthlyProjectPositionAssignmentUpdateComponent from '@/entities/monthly-project-position-assignment/monthly-project-position-assignment-update.vue';
import MonthlyProjectPositionAssignmentClass from '@/entities/monthly-project-position-assignment/monthly-project-position-assignment-update.component';
import MonthlyProjectPositionAssignmentService from '@/entities/monthly-project-position-assignment/monthly-project-position-assignment.service';

import ProjectPositionService from '@/entities/project-position/project-position.service';

import PersonService from '@/entities/person/person.service';
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
  describe('MonthlyProjectPositionAssignment Management Update Component', () => {
    let wrapper: Wrapper<MonthlyProjectPositionAssignmentClass>;
    let comp: MonthlyProjectPositionAssignmentClass;
    let monthlyProjectPositionAssignmentServiceStub: SinonStubbedInstance<MonthlyProjectPositionAssignmentService>;

    beforeEach(() => {
      monthlyProjectPositionAssignmentServiceStub = sinon.createStubInstance<MonthlyProjectPositionAssignmentService>(
        MonthlyProjectPositionAssignmentService
      );

      wrapper = shallowMount<MonthlyProjectPositionAssignmentClass>(MonthlyProjectPositionAssignmentUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          monthlyProjectPositionAssignmentService: () => monthlyProjectPositionAssignmentServiceStub,
          alertService: () => new AlertService(),

          projectPositionService: () =>
            sinon.createStubInstance<ProjectPositionService>(ProjectPositionService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          personService: () =>
            sinon.createStubInstance<PersonService>(PersonService, {
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
        comp.monthlyProjectPositionAssignment = entity;
        monthlyProjectPositionAssignmentServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(monthlyProjectPositionAssignmentServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.monthlyProjectPositionAssignment = entity;
        monthlyProjectPositionAssignmentServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(monthlyProjectPositionAssignmentServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundMonthlyProjectPositionAssignment = { id: 123 };
        monthlyProjectPositionAssignmentServiceStub.find.resolves(foundMonthlyProjectPositionAssignment);
        monthlyProjectPositionAssignmentServiceStub.retrieve.resolves([foundMonthlyProjectPositionAssignment]);

        // WHEN
        comp.beforeRouteEnter({ params: { monthlyProjectPositionAssignmentId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.monthlyProjectPositionAssignment).toBe(foundMonthlyProjectPositionAssignment);
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
