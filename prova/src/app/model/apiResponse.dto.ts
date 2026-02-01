

export interface ApiResponseDto<T>{
  dto: any;

  success:boolean;
  message:string;
  data: T;
}
