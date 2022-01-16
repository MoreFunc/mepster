import { Component, Provide, Vue } from 'vue-property-decorator';

import UserService from '@/entities/user/user.service';
import OrganizationService from './organization/organization.service';
import PersonService from './person/person.service';
import RoleService from './role/role.service';
import ProjectService from './project/project.service';
import ProjectPositionService from './project-position/project-position.service';
import SkillService from './skill/skill.service';
import MonthlyProjectPositionAssignmentService from './monthly-project-position-assignment/monthly-project-position-assignment.service';
import MonthlyAvailabilityService from './monthly-availability/monthly-availability.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

@Component
export default class Entities extends Vue {
  @Provide('userService') private userService = () => new UserService();
  @Provide('organizationService') private organizationService = () => new OrganizationService();
  @Provide('personService') private personService = () => new PersonService();
  @Provide('roleService') private roleService = () => new RoleService();
  @Provide('projectService') private projectService = () => new ProjectService();
  @Provide('projectPositionService') private projectPositionService = () => new ProjectPositionService();
  @Provide('skillService') private skillService = () => new SkillService();
  @Provide('monthlyProjectPositionAssignmentService') private monthlyProjectPositionAssignmentService = () =>
    new MonthlyProjectPositionAssignmentService();
  @Provide('monthlyAvailabilityService') private monthlyAvailabilityService = () => new MonthlyAvailabilityService();
  // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
}
