/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import ProjectPositionDetailComponent from '@/entities/project-position/project-position-details.vue';
import ProjectPositionClass from '@/entities/project-position/project-position-details.component';
import ProjectPositionService from '@/entities/project-position/project-position.service';
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
  describe('ProjectPosition Management Detail Component', () => {
    let wrapper: Wrapper<ProjectPositionClass>;
    let comp: ProjectPositionClass;
    let projectPositionServiceStub: SinonStubbedInstance<ProjectPositionService>;

    beforeEach(() => {
      projectPositionServiceStub = sinon.createStubInstance<ProjectPositionService>(ProjectPositionService);

      wrapper = shallowMount<ProjectPositionClass>(ProjectPositionDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { projectPositionService: () => projectPositionServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundProjectPosition = { id: 123 };
        projectPositionServiceStub.find.resolves(foundProjectPosition);

        // WHEN
        comp.retrieveProjectPosition(123);
        await comp.$nextTick();

        // THEN
        expect(comp.projectPosition).toBe(foundProjectPosition);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundProjectPosition = { id: 123 };
        projectPositionServiceStub.find.resolves(foundProjectPosition);

        // WHEN
        comp.beforeRouteEnter({ params: { projectPositionId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.projectPosition).toBe(foundProjectPosition);
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
