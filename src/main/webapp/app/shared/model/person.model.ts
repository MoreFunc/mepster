import { ISkill } from '@/shared/model/skill.model';
import { IOrganization } from '@/shared/model/organization.model';
import { IMonthlyProjectPositionAssignment } from '@/shared/model/monthly-project-position-assignment.model';

export interface IPerson {
  id?: number;
  firstname?: string;
  lastname?: string;
  notes?: string | null;
  skills?: ISkill[] | null;
  organization?: IOrganization | null;
  monthlyAssignments?: IMonthlyProjectPositionAssignment[] | null;
}

export class Person implements IPerson {
  constructor(
    public id?: number,
    public firstname?: string,
    public lastname?: string,
    public notes?: string | null,
    public skills?: ISkill[] | null,
    public organization?: IOrganization | null,
    public monthlyAssignments?: IMonthlyProjectPositionAssignment[] | null
  ) {}
}