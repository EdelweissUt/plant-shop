import React from 'react';
import { styled } from '@mui/material/styles';
import Grid from '@mui/material/Grid';
import Paper from '@mui/material/Paper';
import Box from '@mui/material/Box';
import BlogItem from "./BlogItem.jsx";

const Item = styled(Paper)(({ theme }) => ({
    backgroundColor: '#fff',
    ...theme.typography.body2,
    padding: theme.spacing(1),
    textAlign: 'center',
    color: (theme.vars ?? theme).palette.text.secondary,
    ...theme.applyStyles('dark', {
        backgroundColor: '#1A2027',
    }),
}));
function BlogSlider(props) {
    return (
       <div className='mt-8 mb-8 '>
           <Box sx={{ flexGrow: 1 }}>
               <Grid container spacing={{ xs: 2, md: 3 }} columns={{ xs: 4, sm: 8, md: 12 }}>
                   {Array.from(Array(3)).map((_, index) => (
                       <Grid key={index} size={{ xs: 2, sm: 4, md: 4 }}>
                           <Item ><BlogItem/></Item>
                       </Grid>
                   ))}
               </Grid>
           </Box>
       </div>
    );
}

export default BlogSlider;