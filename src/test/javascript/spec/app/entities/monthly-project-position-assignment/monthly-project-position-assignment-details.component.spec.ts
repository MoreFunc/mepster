/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import MonthlyProjectPositionAssignmentDetailComponent from '@/entities/monthly-project-position-assignment/monthly-project-position-assignment-details.vue';
import MonthlyProjectPositionAssignmentClass from '@/entities/monthly-project-position-assignment/monthly-project-position-assignment-details.component';
import MonthlyProjectPositionAssignmentService from '@/entities/monthly-project-position-assignment/monthly-project-position-assignment.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('MonthlyProjectPositionAssignment Management Detail Component', () => {
    let wrapper: Wrapper<MonthlyProjectPositionAssignmentClass>;
    let comp: MonthlyProjectPositionAssignmentClass;
    let monthlyProjectPositionAssignmentServiceStub: SinonStubbedInstance<MonthlyProjectPositionAssignmentService>;

    beforeEach(() => {
      monthlyProjectPositionAssignmentServiceStub = sinon.createStubInstance<MonthlyProjectPositionAssignmentService>(
        MonthlyProjectPositionAssignmentService
      );

      wrapper = shallowMount<MonthlyProjectPositionAssignmentClass>(MonthlyProjectPositionAssignmentDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          monthlyProjectPositionAssignmentService: () => monthlyProjectPositionAssignmentServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundMonthlyProjectPositionAssignment = { id: 123 };
        monthlyProjectPositionAssignmentServiceStub.find.resolves(foundMonthlyProjectPositionAssignment);

        // WHEN
        comp.retrieveMonthlyProjectPositionAssignment(123);
        await comp.$nextTick();

        // THEN
        expect(comp.monthlyProjectPositionAssignment).toBe(foundMonthlyProjectPositionAssignment);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundMonthlyProjectPositionAssignment = { id: 123 };
        monthlyProjectPositionAssignmentServiceStub.find.resolves(foundMonthlyProjectPositionAssignment);

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
