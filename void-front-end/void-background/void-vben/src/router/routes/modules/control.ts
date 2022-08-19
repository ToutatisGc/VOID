import type { AppRouteModule } from '/@/router/types';

import { LAYOUT } from '/@/router/constant';
import { t } from '/@/hooks/web/useI18n';

const dashboard: AppRouteModule = {
  path: '/control',
  name: 'Control',
  component: LAYOUT,
  redirect: '/control/index',
  meta: {
    icon: 'material-symbols:view-in-ar-outline-sharp',
    title: t('routes.basic.control.index'),
    orderNo: 100000,
  },
  children: [
    {
      path: 'menuSetting',
      name: 'MenuSetting',
      component: () => import('/@/views/sys/control/index.vue'),
      meta: {
        title: t('routes.basic.control.menuSetting'),
        icon: 'material-symbols:view-timeline-outline-sharp',
      },
    },
    {
      path: 'about',
      name: 'AboutPage',
      component: () => import('/@/views/sys/about/index.vue'),
      meta: {
        title: t('routes.dashboard.about'),
        icon: 'simple-icons:about-dot-me',
      },
    },
  ],
};

export default dashboard;
