import React, { FC } from "react";
import styled from "styled-components";

//compoments
import DockerItemCard from "./DockerItemCard";

interface Props {
  datas: {
    title: string;
    items: string[];
  }[];
}

const DockerCat: FC<Props> = ({ datas }) => {
  return (
    <>
      {datas.map((data) => {
        return (
          <>
            <Title>{data.title}</Title>
            <DockerItemCard items={data.items} />
          </>
        );
      })}
    </>
  );
};

export default DockerCat;

const Title = styled.h1`
  font-size: 20px;
`;
