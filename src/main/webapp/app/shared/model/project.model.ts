import { IProjectPosition } from '@/shared/model/project-position.model';
import { IOrganization } from '@/shared/model/organization.model';

export interface IProject {
  id?: number;
  title?: string;
  description?: string | null;
  start?: Date | null;
  end?: Date | null;
  active?: boolean;
  notes?: string | null;
  projectPositions?: IProjectPosition[];
  organization?: IOrganization;
}

export class Project implements IProject {
  constructor(
    public id?: number,
    public title?: string,
    public description?: string | null,
    public start?: Date | null,
    public end?: Date | null,
    public active?: boolean,
    public notes?: string | null,
    public projectPositions?: IProjectPosition[],
    public organization?: IOrganization
  ) {
    this.active = this.active ?? false;
  }
}
