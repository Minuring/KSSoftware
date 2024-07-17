import React from 'react';
import { withStyles, makeStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
const StyledTableCell = withStyles((theme) => ({
    head: {
        backgroundColor: theme.palette.common.white,
        color: theme.palette.common.black,
        fontWeight : 'bold'
    },
    body: {
        fontSize: 14,
        fontWeight: 'bold'
    },
}))(TableCell);
const StyledTableRow = withStyles((theme) => ({
    root: {
        '&:nth-of-type(odd)': {
            backgroundColor: theme.palette.action.hover,
        },
    },
}))(TableRow);
const useStyles = makeStyles({
    table: {
        minWidth: 700,
    },
});
export default function CustomizedTables(props) {
    const classes = useStyles();
    const inputHeadings = props.headings;
    const rows = [];
    const inputRows = props.rows;
    inputRows.map((inputRow) => {
        let row = [];
        inputRow.map((e) => {
            row.push(e);
        })
        rows.push(row);
    })

    return (
        <TableContainer component={Paper} style={{marginTop:'3%', marginBottom:'3%'}}>
            <Table className={classes.table} aria-label="customized table">
                <TableHead>
                    <TableRow>
                        {inputHeadings.map(h => (
                            <StyledTableCell align={props.align}>{h}</StyledTableCell>
                        ))}
                    </TableRow>
                </TableHead>
                <TableBody>
                    {rows.map((row) => (
                        <StyledTableRow>
                            {row.map((e => (
                                <StyledTableCell align={props.align}>{e}</StyledTableCell>
                            )))}
                        </StyledTableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    );
}