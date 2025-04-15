// User Role Types
export enum UserRole {
  ADMIN = 'ADMIN',
  SUPPLIER = 'SUPPLIER',
  DESIGNER = 'DESIGNER',
}

// User Status Types
export enum UserStatus {
  ACTIVE = 'ACTIVE',
  INACTIVE = 'INACTIVE',
  PENDING = 'PENDING',
}

// User Model
export interface User {
  id: string;
  email: string;
  name: string;
  role: UserRole;
  status: UserStatus;
  companyName?: string;
  phoneNumber?: string;
  createdAt: Date;
  updatedAt: Date;
  lastLoginAt?: Date;
}

// Permission Types
export enum PermissionType {
  READ = 'READ',
  WRITE = 'WRITE',
  DELETE = 'DELETE',
  MANAGE = 'MANAGE',
}

// Resource Types
export enum ResourceType {
  MATERIAL = 'MATERIAL',
  QUOTATION = 'QUOTATION',
  PROJECT = 'PROJECT',
  USER = 'USER',
  COMPANY = 'COMPANY',
}

// Permission Model
export interface Permission {
  id: string;
  resourceType: ResourceType;
  permissionType: PermissionType;
  description: string;
}

// Role Permission Model
export interface RolePermission {
  role: UserRole;
  permissions: Permission[];
}

// Default Role Permissions
export const DEFAULT_ROLE_PERMISSIONS: Record<UserRole, Permission[]> = {
  [UserRole.ADMIN]: [
    { id: '1', resourceType: ResourceType.USER, permissionType: PermissionType.MANAGE, description: 'Manage users' },
    { id: '2', resourceType: ResourceType.MATERIAL, permissionType: PermissionType.MANAGE, description: 'Manage materials' },
    { id: '3', resourceType: ResourceType.QUOTATION, permissionType: PermissionType.MANAGE, description: 'Manage quotations' },
    { id: '4', resourceType: ResourceType.PROJECT, permissionType: PermissionType.MANAGE, description: 'Manage projects' },
  ],
  [UserRole.SUPPLIER]: [
    { id: '5', resourceType: ResourceType.MATERIAL, permissionType: PermissionType.WRITE, description: 'Manage own materials' },
    { id: '6', resourceType: ResourceType.QUOTATION, permissionType: PermissionType.WRITE, description: 'Create and manage quotations' },
    { id: '7', resourceType: ResourceType.PROJECT, permissionType: PermissionType.READ, description: 'View projects' },
  ],
  [UserRole.DESIGNER]: [
    { id: '8', resourceType: ResourceType.MATERIAL, permissionType: PermissionType.READ, description: 'View materials' },
    { id: '9', resourceType: ResourceType.QUOTATION, permissionType: PermissionType.WRITE, description: 'Create and manage quotations' },
    { id: '10', resourceType: ResourceType.PROJECT, permissionType: PermissionType.MANAGE, description: 'Manage own projects' },
  ],
};

// Helper function to check permissions
export function hasPermission(
  user: User,
  resourceType: ResourceType,
  permissionType: PermissionType
): boolean {
  const userPermissions = DEFAULT_ROLE_PERMISSIONS[user.role];
  return userPermissions.some(
    (permission) =>
      permission.resourceType === resourceType &&
      permission.permissionType === permissionType
  );
} 