import React, { FC } from "react";
import styled from "styled-components";

interface Props {
  items: string[];
}

const DockerItemCard: FC<Props> = ({ items }) => {
  return <>hi</>;
};

export default DockerItemCard;

const Container = styled.div`
  width: 100px;
  height: 100px;
  border: 1px solid black;
  margin: 10px;
  border-radius: 10px;
`;
