import axios from 'axios';

import { IMonthlyProjectPositionAssignment } from '@/shared/model/monthly-project-position-assignment.model';

const baseApiUrl = 'api/monthly-project-position-assignments';

export default class MonthlyProjectPositionAssignmentService {
  public find(id: number): Promise<IMonthlyProjectPositionAssignment> {
    return new Promise<IMonthlyProjectPositionAssignment>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${id}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public retrieve(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrl)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public delete(id: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .delete(`${baseApiUrl}/${id}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public create(entity: IMonthlyProjectPositionAssignment): Promise<IMonthlyProjectPositionAssignment> {
    return new Promise<IMonthlyProjectPositionAssignment>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public update(entity: IMonthlyProjectPositionAssignment): Promise<IMonthlyProjectPositionAssignment> {
    return new Promise<IMonthlyProjectPositionAssignment>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}/${entity.id}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public partialUpdate(entity: IMonthlyProjectPositionAssignment): Promise<IMonthlyProjectPositionAssignment> {
    return new Promise<IMonthlyProjectPositionAssignment>((resolve, reject) => {
      axios
        .patch(`${baseApiUrl}/${entity.id}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
