export interface UserProps {
  username: string;
  email: string;
  password: string;
  confirmPassword: string;
}

export interface UserPrincipal {
  id: number;
  email: string;
  username: string;
  authority: Authority;
  accessToken: string;
  enabled: boolean;
}

export enum Authority {
  ADMIN = "ADMIN",
  USER = "USER",
}