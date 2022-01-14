/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import MonthlyProjectPositionAssignmentComponent from '@/entities/monthly-project-position-assignment/monthly-project-position-assignment.vue';
import MonthlyProjectPositionAssignmentClass from '@/entities/monthly-project-position-assignment/monthly-project-position-assignment.component';
import MonthlyProjectPositionAssignmentService from '@/entities/monthly-project-position-assignment/monthly-project-position-assignment.service';
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
  describe('MonthlyProjectPositionAssignment Management Component', () => {
    let wrapper: Wrapper<MonthlyProjectPositionAssignmentClass>;
    let comp: MonthlyProjectPositionAssignmentClass;
    let monthlyProjectPositionAssignmentServiceStub: SinonStubbedInstance<MonthlyProjectPositionAssignmentService>;

    beforeEach(() => {
      monthlyProjectPositionAssignmentServiceStub = sinon.createStubInstance<MonthlyProjectPositionAssignmentService>(
        MonthlyProjectPositionAssignmentService
      );
      monthlyProjectPositionAssignmentServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<MonthlyProjectPositionAssignmentClass>(MonthlyProjectPositionAssignmentComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          monthlyProjectPositionAssignmentService: () => monthlyProjectPositionAssignmentServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      monthlyProjectPositionAssignmentServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllMonthlyProjectPositionAssignments();
      await comp.$nextTick();

      // THEN
      expect(monthlyProjectPositionAssignmentServiceStub.retrieve.called).toBeTruthy();
      expect(comp.monthlyProjectPositionAssignments[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      monthlyProjectPositionAssignmentServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(monthlyProjectPositionAssignmentServiceStub.retrieve.callCount).toEqual(1);

      comp.removeMonthlyProjectPositionAssignment();
      await comp.$nextTick();

      // THEN
      expect(monthlyProjectPositionAssignmentServiceStub.delete.called).toBeTruthy();
      expect(monthlyProjectPositionAssignmentServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
