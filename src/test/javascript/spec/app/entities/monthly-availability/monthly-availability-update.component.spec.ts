/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import MonthlyAvailabilityUpdateComponent from '@/entities/monthly-availability/monthly-availability-update.vue';
import MonthlyAvailabilityClass from '@/entities/monthly-availability/monthly-availability-update.component';
import MonthlyAvailabilityService from '@/entities/monthly-availability/monthly-availability.service';

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
  describe('MonthlyAvailability Management Update Component', () => {
    let wrapper: Wrapper<MonthlyAvailabilityClass>;
    let comp: MonthlyAvailabilityClass;
    let monthlyAvailabilityServiceStub: SinonStubbedInstance<MonthlyAvailabilityService>;

    beforeEach(() => {
      monthlyAvailabilityServiceStub = sinon.createStubInstance<MonthlyAvailabilityService>(MonthlyAvailabilityService);

      wrapper = shallowMount<MonthlyAvailabilityClass>(MonthlyAvailabilityUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          monthlyAvailabilityService: () => monthlyAvailabilityServiceStub,
          alertService: () => new AlertService(),

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
        comp.monthlyAvailability = entity;
        monthlyAvailabilityServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(monthlyAvailabilityServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.monthlyAvailability = entity;
        monthlyAvailabilityServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(monthlyAvailabilityServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundMonthlyAvailability = { id: 123 };
        monthlyAvailabilityServiceStub.find.resolves(foundMonthlyAvailability);
        monthlyAvailabilityServiceStub.retrieve.resolves([foundMonthlyAvailability]);

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
