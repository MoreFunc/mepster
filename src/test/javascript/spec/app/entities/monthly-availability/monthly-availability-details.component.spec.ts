/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import MonthlyAvailabilityDetailComponent from '@/entities/monthly-availability/monthly-availability-details.vue';
import MonthlyAvailabilityClass from '@/entities/monthly-availability/monthly-availability-details.component';
import MonthlyAvailabilityService from '@/entities/monthly-availability/monthly-availability.service';
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
  describe('MonthlyAvailability Management Detail Component', () => {
    let wrapper: Wrapper<MonthlyAvailabilityClass>;
    let comp: MonthlyAvailabilityClass;
    let monthlyAvailabilityServiceStub: SinonStubbedInstance<MonthlyAvailabilityService>;

    beforeEach(() => {
      monthlyAvailabilityServiceStub = sinon.createStubInstance<MonthlyAvailabilityService>(MonthlyAvailabilityService);

      wrapper = shallowMount<MonthlyAvailabilityClass>(MonthlyAvailabilityDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { monthlyAvailabilityService: () => monthlyAvailabilityServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundMonthlyAvailability = { id: 123 };
        monthlyAvailabilityServiceStub.find.resolves(foundMonthlyAvailability);

        // WHEN
        comp.retrieveMonthlyAvailability(123);
        await comp.$nextTick();

        // THEN
        expect(comp.monthlyAvailability).toBe(foundMonthlyAvailability);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundMonthlyAvailability = { id: 123 };
        monthlyAvailabilityServiceStub.find.resolves(foundMonthlyAvailability);

        // WHEN
        comp.beforeRouteEnter({ params: { monthlyAvailabilityId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.monthlyAvailability).toBe(foundMonthlyAvailability);
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
