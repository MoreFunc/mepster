/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import MonthlyAvailabilityComponent from '@/entities/monthly-availability/monthly-availability.vue';
import MonthlyAvailabilityClass from '@/entities/monthly-availability/monthly-availability.component';
import MonthlyAvailabilityService from '@/entities/monthly-availability/monthly-availability.service';
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
  describe('MonthlyAvailability Management Component', () => {
    let wrapper: Wrapper<MonthlyAvailabilityClass>;
    let comp: MonthlyAvailabilityClass;
    let monthlyAvailabilityServiceStub: SinonStubbedInstance<MonthlyAvailabilityService>;

    beforeEach(() => {
      monthlyAvailabilityServiceStub = sinon.createStubInstance<MonthlyAvailabilityService>(MonthlyAvailabilityService);
      monthlyAvailabilityServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<MonthlyAvailabilityClass>(MonthlyAvailabilityComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          monthlyAvailabilityService: () => monthlyAvailabilityServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      monthlyAvailabilityServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllMonthlyAvailabilitys();
      await comp.$nextTick();

      // THEN
      expect(monthlyAvailabilityServiceStub.retrieve.called).toBeTruthy();
      expect(comp.monthlyAvailabilities[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      monthlyAvailabilityServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(monthlyAvailabilityServiceStub.retrieve.callCount).toEqual(1);

      comp.removeMonthlyAvailability();
      await comp.$nextTick();

      // THEN
      expect(monthlyAvailabilityServiceStub.delete.called).toBeTruthy();
      expect(monthlyAvailabilityServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
