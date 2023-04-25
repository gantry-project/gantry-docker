import React, {useState} from "react";
import styled from "@emotion/styled";
import MyContainerItem from "./MyContainerItem";
import {useQuery} from "@tanstack/react-query";
import axios from "axios";
import config from "../../config/config";
import {getAuthUser} from "../../api/user";

interface MyContainerDto {
  id: string;
  applicationId: number;
  status: string;
}

interface MyContainer {
  id: string;
  applicationId: number;
  status: string;
}

const MyContainerList = () => {
  function convertFromDto(data: MyContainerDto): MyContainer {
    return {
      id: data.id,
      applicationId: data.applicationId,
      status: data.status,
    };
  }

  const {isLoading, isFetching, error, data: containers } = useQuery<MyContainerDto[], Error, MyContainerDto[]>(
    ["getMyContainers"],
    () => {
      const headers: any = {};
      const token = getAuthUser()?.accessToken;
      if (token) {
        headers["Authorization"] = "Bearer " + token;
      }
      return axios.get<MyContainerDto[]>(`${config.gantryApiUrl}/containers`, {headers: {...headers}})
        .then((res) => res.data.map(i => convertFromDto(i)));
    }
  );

  if (isLoading) {
    return <h1>Loading...</h1>;
  }
  // else if (isFetching) {
  //   return <h1>Updating...</h1>;
  // }
  else if (error) {
    return <h1>{"An error has occurred: " + error.toString()}</h1>;
  }

  return <>
    {containers.map(c => <MyContainerItem key={c.id} id={c.id} status={c.status}/>)}
  </>
};

export default MyContainerList;
