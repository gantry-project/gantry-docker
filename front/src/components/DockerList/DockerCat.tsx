import React, { FC, useCallback, useState } from "react";
import styled from "@emotion/styled";

//compoments
import DockerItemCard from "./DockerItemCard";

interface Props {
  datas: {
    title: string;
    items: string[];
  }[];
  state: boolean;
}

const DockerCat: FC<Props> = ({ datas, state }) => {
  const [isHovered, setIsHovered] = useState(state);
  /**유저가 고른 데이터 */
  const onClickHandler = useCallback((title: string, item: string) => {
    console.log("title", title);
    console.log("item", item);
  }, []);

  return (
    <>
      {datas.map((data) => {
        return (
          <Container>
            <Title data-name="title">{data.title}</Title>
            <ItemsWrapper>
              {data.items.map((item) => {
                return (
                  <ItemWrapper isHovered={state}>
                    <ItemTop
                      data-name={item}
                      onClick={() => onClickHandler(data.title, item)}
                    >
                      {item}
                    </ItemTop>
                    <ItemBottom>
                      <Detail>. . .</Detail>
                    </ItemBottom>
                  </ItemWrapper>
                );
              })}
            </ItemsWrapper>
          </Container>
        );
      })}
    </>
  );
};

export default DockerCat;

const Title = styled.h1`
  font-size: 20px;
  color: gray;
`;
const Container = styled.div`
  display: flex;
  flex-direction: column;

  padding: 15px;
  margin: 10px;
`;

const ItemsWrapper = styled.div`
  display: flex;
  margin: 19px 3px 3px 3px;
  flex-wrap: wrap;
`;

const ItemWrapper = styled.div<{ isHovered: boolean }>`
  min-width: 100px;
  height: 100px;
  border: 1px solid black;
  margin: 10px;
  border-radius: 10px;
  flex-direction: column;
  cursor: pointer;
  ${({ isHovered }) =>
    isHovered &&
    `
    &:hover {
      background-color: #aac9e4;
      transform: scale(1.1);
    }
  `}
`;
const ItemTop = styled.div`
  align-items: center;
  display: flex;
  justify-content: center;
  height: 80%;
`;
const ItemBottom = styled.div`
  width: 100%;
  height: 20%;
  display: flex;
  justify-content: end;
  align-items: center;
`;
const Detail = styled.div`
  width: 30px;
  height: 100%;
`;
