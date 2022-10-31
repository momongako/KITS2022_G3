import {Autocomplete, TextField} from "@mui/material";

export default function ComboBox({list,onSelect,placeholder}) {
    return (
        <Autocomplete
            disablePortal
            id="combo-box-demo"
            options={list}
            getOptionLabel={(item) => item.id +" - "+ item.name}
            isOptionEqualToValue={(option, value) => option.id === value.id}
            sx={{ width: 300 }}
           style={{width: '70%'}}
            renderInput={(params) => <TextField {...params} label={placeholder} />}
            onChange={(event,value)=>onSelect(event,value)}
        />
    );
}