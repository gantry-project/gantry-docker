import React, { useState } from "react";
import styled from "@emotion/styled";

import InputLabel from "@mui/material/InputLabel";
import MenuItem from "@mui/material/MenuItem";
import FormControl from "@mui/material/FormControl";
import Select, { SelectChangeEvent } from "@mui/material/Select";
import SearchIcon from "@mui/icons-material/Search";

//component

const ApplicationListHeader = () => {
  const [containerFilter, setContainerFilter] = useState("");

  const handleChange = (event: SelectChangeEvent) => {
    setContainerFilter(event.target.value as string);
  };
  return (
    <Container>
      <FormControlCustom style={{ width: "150px" }}>
        <InputLabel id="demo-simple-select-label">Contaianer</InputLabel>
        <Select
          labelId="demo-simple-select-label"
          id="demo-simple-select"
          value={containerFilter}
          label="Contaianer"
          onChange={handleChange}
        >
          <MenuItem value={10}>내가 실행한 컨테이너</MenuItem>
          <MenuItem value={20}>Twenty</MenuItem>
          <MenuItem value={30}>Thirty</MenuItem>
        </Select>
      </FormControlCustom>
      <SearchWrapper>
        <SearchIcon />
        <SearchInput />
      </SearchWrapper>
    </Container>
  );
};

export default ApplicationListHeader;

const Container = styled.div`
  height: 60px;
  width: 100%;

  display: flex;
  justify-content: space-between;
  align-items: center;
`;

const FormControlCustom = styled(FormControl)`
  width: 150px;
`;

const SearchWrapper = styled.div`
  display: flex;
  justify-content: center;
`;
const SearchInput = styled.input`
  margin: 5px;
`;
