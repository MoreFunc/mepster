/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import ProjectPositionUpdateComponent from '@/entities/project-position/project-position-update.vue';
import ProjectPositionClass from '@/entities/project-position/project-position-update.component';
import ProjectPositionService from '@/entities/project-position/project-position.service';

import RoleService from '@/entities/role/role.service';

import SkillService from '@/entities/skill/skill.service';

import ProjectService from '@/entities/project/project.service';

import MonthlyProjectPositionAssignmentService from '@/entities/monthly-project-position-assignment/monthly-project-position-assignment.service';
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
  describe('ProjectPosition Management Update Component', () => {
    let wrapper: Wrapper<ProjectPositionClass>;
    let comp: ProjectPositionClass;
    let projectPositionServiceStub: SinonStubbedInstance<ProjectPositionService>;

    beforeEach(() => {
      projectPositionServiceStub = sinon.createStubInstance<ProjectPositionService>(ProjectPositionService);

      wrapper = shallowMount<ProjectPositionClass>(ProjectPositionUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          projectPositionService: () => projectPositionServiceStub,
          alertService: () => new AlertService(),

          roleService: () =>
            sinon.createStubInstance<RoleService>(RoleService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          skillService: () =>
            sinon.createStubInstance<SkillService>(SkillService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          projectService: () =>
            sinon.createStubInstance<ProjectService>(ProjectService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          monthlyProjectPositionAssignmentService: () =>
            sinon.createStubInstance<MonthlyProjectPositionAssignmentService>(MonthlyProjectPositionAssignmentService, {
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
        comp.projectPosition = entity;
        projectPositionServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(projectPositionServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.projectPosition = entity;
        projectPositionServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(projectPositionServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundProjectPosition = { id: 123 };
        projectPositionServiceStub.find.resolves(foundProjectPosition);
        projectPositionServiceStub.retrieve.resolves([foundProjectPosition]);

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
