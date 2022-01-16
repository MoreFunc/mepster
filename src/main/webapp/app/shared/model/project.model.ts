import { IProjectPosition } from '@/shared/model/project-position.model';
import { IOrganization } from '@/shared/model/organization.model';

export interface IProject {
  id?: number;
  title?: string;
  description?: string | null;
  startDate?: Date | null;
  endDate?: Date | null;
  isActive?: boolean;
  chancePercent?: number | null;
  notes?: string | null;
  projectPositions?: IProjectPosition[];
  organization?: IOrganization;
}

export class Project implements IProject {
  constructor(
    public id?: number,
    public title?: string,
    public description?: string | null,
    public startDate?: Date | null,
    public endDate?: Date | null,
    public isActive?: boolean,
    public chancePercent?: number | null,
    public notes?: string | null,
    public projectPositions?: IProjectPosition[],
    public organization?: IOrganization
  ) {
    this.isActive = this.isActive ?? false;
  }
}
