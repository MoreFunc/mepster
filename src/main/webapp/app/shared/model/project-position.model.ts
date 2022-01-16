import { IRole } from '@/shared/model/role.model';
import { ISkill } from '@/shared/model/skill.model';
import { IProject } from '@/shared/model/project.model';
import { IMonthlyProjectPositionAssignment } from '@/shared/model/monthly-project-position-assignment.model';

export interface IProjectPosition {
  id?: number;
  title?: string;
  description?: string | null;
  startDate?: Date | null;
  endDate?: Date | null;
  percent?: number | null;
  role?: IRole;
  skills?: ISkill[] | null;
  project?: IProject;
  monthlyAssignments?: IMonthlyProjectPositionAssignment[] | null;
}

export class ProjectPosition implements IProjectPosition {
  constructor(
    public id?: number,
    public title?: string,
    public description?: string | null,
    public startDate?: Date | null,
    public endDate?: Date | null,
    public percent?: number | null,
    public role?: IRole,
    public skills?: ISkill[] | null,
    public project?: IProject,
    public monthlyAssignments?: IMonthlyProjectPositionAssignment[] | null
  ) {}
}
