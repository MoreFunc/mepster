import { ISkill } from '@/shared/model/skill.model';
import { IRole } from '@/shared/model/role.model';
import { IOrganization } from '@/shared/model/organization.model';
import { IMonthlyProjectPositionAssignment } from '@/shared/model/monthly-project-position-assignment.model';
import { IMonthlyAvailability } from '@/shared/model/monthly-availability.model';

export interface IPerson {
  id?: number;
  firstname?: string;
  lastname?: string;
  type?: string | null;
  lead?: string | null;
  phoneNumber?: string | null;
  email?: string | null;
  notes?: string | null;
  skills?: ISkill[] | null;
  roles?: IRole[] | null;
  organization?: IOrganization | null;
  monthlyAssignments?: IMonthlyProjectPositionAssignment[] | null;
  monthlyAvailabilities?: IMonthlyAvailability[] | null;
}

export class Person implements IPerson {
  constructor(
    public id?: number,
    public firstname?: string,
    public lastname?: string,
    public type?: string | null,
    public lead?: string | null,
    public phoneNumber?: string | null,
    public email?: string | null,
    public notes?: string | null,
    public skills?: ISkill[] | null,
    public roles?: IRole[] | null,
    public organization?: IOrganization | null,
    public monthlyAssignments?: IMonthlyProjectPositionAssignment[] | null,
    public monthlyAvailabilities?: IMonthlyAvailability[] | null
  ) {}
}
