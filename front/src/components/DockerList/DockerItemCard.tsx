<<<<<<< HEAD
import React, { FC } from "react";
import styled from "@emotion/styled";

interface Props {
  items: string[];
}

const DockerItemCard: FC<Props> = ({ items }) => {
  console.log("items", items);
  return (
    <>
      {items.map((item, key) => {
        <Container key={key}>{item} hi</Container>;
      })}
    </>
  );
};

export default DockerItemCard;

const Container = styled.div`
  width: 100px;
  height: 100px;
  border: 1px solid black;
  margin: 10px;
  border-radius: 10px;
`;
=======
import React, { FC } from "react";
import styled from "@emotion/styled";

interface Props {
  items: string[];
}

const DockerItemCard: FC<Props> = ({ items }) => {
  console.log("items", items);
  return (
    <>
      {items.map((item, key) => {
        <Container key={key}>{item} hi</Container>;
      })}
    </>
  );
};

export default DockerItemCard;

const Container = styled.div`
  width: 100px;
  height: 100px;
  border: 1px solid black;
  margin: 10px;
  border-radius: 10px;
`;
>>>>>>> 2916dd479f69ed343ba47b234452038c6e8999f3
