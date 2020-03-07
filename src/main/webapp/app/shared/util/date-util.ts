import * as moment from 'moment';

export const format = (mmt: moment.Moment): string => {
	return mmt.format('YYYY-MM-DD');
}