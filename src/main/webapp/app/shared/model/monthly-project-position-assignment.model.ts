import { IProjectPosition } from '@/shared/model/project-position.model';
import { IPerson } from '@/shared/model/person.model';

export interface IMonthlyProjectPositionAssignment {
  id?: number;
  yearmonth?: Date | null;
  percent?: number | null;
  isActive?: boolean;
  projectPosition?: IProjectPosition;
  person?: IPerson;
}

export class MonthlyProjectPositionAssignment implements IMonthlyProjectPositionAssignment {
  constructor(
    public id?: number,
    public yearmonth?: Date | null,
    public percent?: number | null,
    public isActive?: boolean,
    public projectPosition?: IProjectPosition,
    public person?: IPerson
  ) {
    this.isActive = this.isActive ?? false;
  }
}
