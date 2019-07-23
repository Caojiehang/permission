Permission management system

first part completed: development environment configuration and basic help tools configuration and testing
   1) Spring MVC basic configuration
   2) Mybatis configuration with mySql including using of mybatis generator
   3) Alibaba Druid database management tools
   4) Exception handler configuration
   5) Http request interceptor configuration
   6) Jackson tools and handling of Json data configuration 
   7) Validator configuration
   8) Spring Context configuration
   9) Apache server 
Module development:
Department module completed: function: tree list display, add, delete, edit, update department
Basic UI template design and configutation
User module completed: function: user list display(click department name) include paging display , add, edit, update user
                       User Login Filter, RequestHolder function(obtain current user info and request sessions)
Permission module completed: tree structure display, edit, add, update functions
Permission point module completed: permission list display, add, edit, update permission point
Role module completed: including role management(edit,add,delete) mantain user and role, role and permissions, permissions allocated(Ztree usage), roles allocated
Get user permission data(it can be view on console but without visulization)
Get related role data and user list under certain permission (it can be view on console but without visulization)
Permission interception basic logic implement: 
                      1. Super admin can access all resources (Admin)
                      2. Permission filter: get current login user and allocated permissions, judge whether he has permission to access related resources, if not, jump to no authorited page
                      3. Once user has one permission under one permission module, he is allocated to access the resources
Home page: calendar and event display(will implement backend)
Redis cache usage: 1. For permission interception, store current user permission to the cache
                   2. others also can be added to cache(under consideration)
Operation log: completed log display and search function, recover operation

