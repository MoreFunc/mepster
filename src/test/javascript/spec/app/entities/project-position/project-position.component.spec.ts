/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import ProjectPositionComponent from '@/entities/project-position/project-position.vue';
import ProjectPositionClass from '@/entities/project-position/project-position.component';
import ProjectPositionService from '@/entities/project-position/project-position.service';
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
  describe('ProjectPosition Management Component', () => {
    let wrapper: Wrapper<ProjectPositionClass>;
    let comp: ProjectPositionClass;
    let projectPositionServiceStub: SinonStubbedInstance<ProjectPositionService>;

    beforeEach(() => {
      projectPositionServiceStub = sinon.createStubInstance<ProjectPositionService>(ProjectPositionService);
      projectPositionServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ProjectPositionClass>(ProjectPositionComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          projectPositionService: () => projectPositionServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      projectPositionServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllProjectPositions();
      await comp.$nextTick();

      // THEN
      expect(projectPositionServiceStub.retrieve.called).toBeTruthy();
      expect(comp.projectPositions[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      projectPositionServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(projectPositionServiceStub.retrieve.callCount).toEqual(1);

      comp.removeProjectPosition();
      await comp.$nextTick();

      // THEN
      expect(projectPositionServiceStub.delete.called).toBeTruthy();
      expect(projectPositionServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
