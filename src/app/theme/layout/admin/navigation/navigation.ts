export interface NavigationItem {
  id: string;
  title: string;
  type: 'item' | 'collapse' | 'group';
  translate?: string;
  icon?: string;
  hidden?: boolean;
  url?: string;
  classes?: string;
  exactMatch?: boolean;
  external?: boolean;
  target?: boolean;
  breadcrumbs?: boolean;
  badge?: {
    title?: string;
    type?: string;
  };
  children?: NavigationItem[];
}

export const NavigationItems: NavigationItem[] = [
  {
    id: 'chart',
    title: 'Chart',
    type: 'group',
    icon: 'icon-group',
    children: [
      {
        id: '1',
        title: 'Dashboard',
        type: 'item',
        url: '/charts',
        classes: 'nav-item',
        icon: 'fas fa-chart-bar'
      }
    ]
  },

  {
    id: 'chart',
    title: 'Chart',
    type: 'group',
    icon: 'icon-group',
    children: [
      {
        id: '2',
        title: 'Total Ordering',
        type: 'item',
        url: '/charts',
        classes: 'nav-item',
        icon: 'fas fa-sort-amount-up'
      }
    ]
  },
  {
    id: 'chart',
    title: 'Chart',
    type: 'group',
    icon: 'icon-group',
    children: [
      {
        id: '3',
        title: 'Ordering Behaviour',
        type: 'item',
        url: '/charts',
        classes: 'nav-item',
        icon: 'feather float-end icon-shopping-cart'
      }
    ]
  },
  
  // {
  //   id: 'ui-component',
  //   title: '',
  //   type: 'group',
  //   icon: 'feather icon-home',
  //   children: [
  //     {
  //       id: 'basic',
  //       title: 'Dashboard',
  //       type: 'collapse',
  //       icon: 'feather icon-box',
  //       children: [
  //       ]
  //     }
  //   ]
  // },
  // {
  //   id: 'ui-component',
  //   title: '',
  //   type: 'group',
  //   icon: 'feather icon-home',
  //   children: [
  //     {
  //       id: 'basic',
  //       title: 'Dashboard',
  //       type: 'collapse',
  //       icon: 'feather icon-box',
  //       children: [
  //         {
  //           id: '1',
  //           title: 'Total Ordering',
  //           type: 'item',
  //           url: '/analytics'
  //         },
  //         {
  //           id: '2',
  //           title: 'Total Ordering Pivots',
  //           type: 'item',
  //           url: '/analytics'
  //         },
  //         {
  //           id: '3',
  //           title: 'Ordering Behaviour',
  //           type: 'item',
  //           url: '/analytics'
  //         },
  //         {
  //           id: '4',
  //           title: 'Day & Time Analysis',
  //           type: 'item',
  //           url: '/analytics'
  //         },
          
  //         // {
  //         //   id: 'badges',
  //         //   title: 'Badges',
  //         //   type: 'item',
  //         //   url: '/component/badges'
  //         // },
  //         // {
  //         //   id: 'breadcrumb-pagination',
  //         //   title: 'Breadcrumb & Pagination',
  //         //   type: 'item',
  //         //   url: '/component/breadcrumb-paging'
  //         // },
  //         // {
  //         //   id: 'collapse',
  //         //   title: 'Collapse',
  //         //   type: 'item',
  //         //   url: '/component/collapse'
  //         // },
  //         // {
  //         //   id: 'tabs-pills',
  //         //   title: 'Tabs & Pills',
  //         //   type: 'item',
  //         //   url: '/component/tabs-pills'
  //         // },
  //         // {
  //         //   id: 'typography',
  //         //   title: 'Typography',
  //         //   type: 'item',
  //         //   url: '/component/typography'
  //         // }
  //       ]
  //     }
  //   ]
  // },
  // {
  //   id: 'Authentication',
  //   title: 'Authentication',
  //   type: 'group',
  //   icon: 'icon-group',
  //   children: [
  //     {
  //       id: 'signup',
  //       title: 'Sign up',
  //       type: 'item',
  //       url: '/auth/signup',
  //       icon: 'feather icon-at-sign',
  //       target: true,
  //       breadcrumbs: false
  //     },
  //     {
  //       id: 'signin',
  //       title: 'Sign in',
  //       type: 'item',
  //       url: '/auth/signin',
  //       icon: 'feather icon-log-in',
  //       target: true,
  //       breadcrumbs: false
  //     }
  //   ]
  // },
  // {
  //   id: 'chart',
  //   title: 'Chart',
  //   type: 'group',
  //   icon: 'icon-group',
  //   children: [
  //     {
  //       id: 'apexchart',
  //       title: 'ApexChart',
  //       type: 'item',
  //       url: '/chart',
  //       classes: 'nav-item',
  //       icon: 'feather icon-pie-chart'
  //     }
  //   ]
  // },
  // {
  //   id: 'forms & tables',
  //   title: 'Forms & Tables',
  //   type: 'group',
  //   icon: 'icon-group',
  //   children: [
  //     {
  //       id: 'forms',
  //       title: 'Basic Elements',
  //       type: 'item',
  //       url: '/forms',
  //       classes: 'nav-item',
  //       icon: 'feather icon-file-text'
  //     },
  //     {
  //       id: 'tables',
  //       title: 'tables',
  //       type: 'item',
  //       url: '/tables',
  //       classes: 'nav-item',
  //       icon: 'feather icon-server'
  //     }
  //   ]
  // },
  // {
  //   id: 'other',
  //   title: 'Other',
  //   type: 'group',
  //   icon: 'icon-group',
  //   children: [
  //     {
  //       id: 'sample-page',
  //       title: 'Sample Page',
  //       type: 'item',
  //       url: '/sample-page',
  //       classes: 'nav-item',
  //       icon: 'feather icon-sidebar'
  //     },
  //     {
  //       id: 'menu-level',
  //       title: 'Menu Levels',
  //       type: 'collapse',
  //       icon: 'feather icon-menu',
  //       children: [
  //         {
  //           id: 'menu-level-2.1',
  //           title: 'Menu Level 2.1',
  //           type: 'item',
  //           url: 'javascript:',
  //           external: true
  //         },
  //         {
  //           id: 'menu-level-2.2',
  //           title: 'Menu Level 2.2',
  //           type: 'collapse',
  //           children: [
  //             {
  //               id: 'menu-level-2.2.1',
  //               title: 'Menu Level 2.2.1',
  //               type: 'item',
  //               url: 'javascript:',
  //               external: true
  //             },
  //             {
  //               id: 'menu-level-2.2.2',
  //               title: 'Menu Level 2.2.2',
  //               type: 'item',
  //               url: 'javascript:',
  //               external: true
  //             }
  //           ]
  //         }
  //       ]
  //     }
  //   ]
  // }
];
