<<<<<<< HEAD
import React from "react";
import styled from "@emotion/styled";

//component

const DockerListHeader = () => {
  return (
    <Container>
      <OptionWrapper>
        <OptionSelect>
          <option value="americano">아메리카노</option>
          <option value="caffe latte">카페라테</option>
          <option value="cafe au lait" selected>
            옵션
          </option>
          <option value="espresso">에스프레소</option>
        </OptionSelect>
      </OptionWrapper>
    </Container>
  );
};

export default DockerListHeader;

const Container = styled.div`
  height: 60px;

  display: felx;
  justify-content: center;
  align-items: center;
`;
const OptionWrapper = styled.div``;

const OptionSelect = styled.select``;
=======
import React from "react";
import styled from "@emotion/styled";

//component

const DockerListHeader = () => {
  return (
    <Container>
      <OptionWrapper>
        <OptionSelect>
          <option value="americano">아메리카노</option>
          <option value="caffe latte">카페라테</option>
          <option value="cafe au lait" selected>
            옵션
          </option>
          <option value="espresso">에스프레소</option>
        </OptionSelect>
      </OptionWrapper>
    </Container>
  );
};

export default DockerListHeader;

const Container = styled.div`
  height: 60px;

  display: felx;
  justify-content: center;
  align-items: center;
`;
const OptionWrapper = styled.div``;

const OptionSelect = styled.select``;
>>>>>>> 2916dd479f69ed343ba47b234452038c6e8999f3
