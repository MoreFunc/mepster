import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

const Organization = () => import('@/entities/organization/organization.vue');
const OrganizationUpdate = () => import('@/entities/organization/organization-update.vue');
const OrganizationDetails = () => import('@/entities/organization/organization-details.vue');

const Person = () => import('@/entities/person/person.vue');
const PersonUpdate = () => import('@/entities/person/person-update.vue');
const PersonDetails = () => import('@/entities/person/person-details.vue');

const Role = () => import('@/entities/role/role.vue');
const RoleUpdate = () => import('@/entities/role/role-update.vue');
const RoleDetails = () => import('@/entities/role/role-details.vue');

const Project = () => import('@/entities/project/project.vue');
const ProjectUpdate = () => import('@/entities/project/project-update.vue');
const ProjectDetails = () => import('@/entities/project/project-details.vue');

const ProjectPosition = () => import('@/entities/project-position/project-position.vue');
const ProjectPositionUpdate = () => import('@/entities/project-position/project-position-update.vue');
const ProjectPositionDetails = () => import('@/entities/project-position/project-position-details.vue');

const Skill = () => import('@/entities/skill/skill.vue');
const SkillUpdate = () => import('@/entities/skill/skill-update.vue');
const SkillDetails = () => import('@/entities/skill/skill-details.vue');

const MonthlyProjectPositionAssignment = () =>
  import('@/entities/monthly-project-position-assignment/monthly-project-position-assignment.vue');
const MonthlyProjectPositionAssignmentUpdate = () =>
  import('@/entities/monthly-project-position-assignment/monthly-project-position-assignment-update.vue');
const MonthlyProjectPositionAssignmentDetails = () =>
  import('@/entities/monthly-project-position-assignment/monthly-project-position-assignment-details.vue');

const MonthlyAvailability = () => import('@/entities/monthly-availability/monthly-availability.vue');
const MonthlyAvailabilityUpdate = () => import('@/entities/monthly-availability/monthly-availability-update.vue');
const MonthlyAvailabilityDetails = () => import('@/entities/monthly-availability/monthly-availability-details.vue');

// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'organization',
      name: 'Organization',
      component: Organization,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'organization/new',
      name: 'OrganizationCreate',
      component: OrganizationUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'organization/:organizationId/edit',
      name: 'OrganizationEdit',
      component: OrganizationUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'organization/:organizationId/view',
      name: 'OrganizationView',
      component: OrganizationDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'person',
      name: 'Person',
      component: Person,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'person/new',
      name: 'PersonCreate',
      component: PersonUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'person/:personId/edit',
      name: 'PersonEdit',
      component: PersonUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'person/:personId/view',
      name: 'PersonView',
      component: PersonDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'role',
      name: 'Role',
      component: Role,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'role/new',
      name: 'RoleCreate',
      component: RoleUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'role/:roleId/edit',
      name: 'RoleEdit',
      component: RoleUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'role/:roleId/view',
      name: 'RoleView',
      component: RoleDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'project',
      name: 'Project',
      component: Project,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'project/new',
      name: 'ProjectCreate',
      component: ProjectUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'project/:projectId/edit',
      name: 'ProjectEdit',
      component: ProjectUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'project/:projectId/view',
      name: 'ProjectView',
      component: ProjectDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'project-position',
      name: 'ProjectPosition',
      component: ProjectPosition,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'project-position/new',
      name: 'ProjectPositionCreate',
      component: ProjectPositionUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'project-position/:projectPositionId/edit',
      name: 'ProjectPositionEdit',
      component: ProjectPositionUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'project-position/:projectPositionId/view',
      name: 'ProjectPositionView',
      component: ProjectPositionDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'skill',
      name: 'Skill',
      component: Skill,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'skill/new',
      name: 'SkillCreate',
      component: SkillUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'skill/:skillId/edit',
      name: 'SkillEdit',
      component: SkillUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'skill/:skillId/view',
      name: 'SkillView',
      component: SkillDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'monthly-project-position-assignment',
      name: 'MonthlyProjectPositionAssignment',
      component: MonthlyProjectPositionAssignment,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'monthly-project-position-assignment/new',
      name: 'MonthlyProjectPositionAssignmentCreate',
      component: MonthlyProjectPositionAssignmentUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'monthly-project-position-assignment/:monthlyProjectPositionAssignmentId/edit',
      name: 'MonthlyProjectPositionAssignmentEdit',
      component: MonthlyProjectPositionAssignmentUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'monthly-project-position-assignment/:monthlyProjectPositionAssignmentId/view',
      name: 'MonthlyProjectPositionAssignmentView',
      component: MonthlyProjectPositionAssignmentDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'monthly-availability',
      name: 'MonthlyAvailability',
      component: MonthlyAvailability,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'monthly-availability/new',
      name: 'MonthlyAvailabilityCreate',
      component: MonthlyAvailabilityUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'monthly-availability/:monthlyAvailabilityId/edit',
      name: 'MonthlyAvailabilityEdit',
      component: MonthlyAvailabilityUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'monthly-availability/:monthlyAvailabilityId/view',
      name: 'MonthlyAvailabilityView',
      component: MonthlyAvailabilityDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
