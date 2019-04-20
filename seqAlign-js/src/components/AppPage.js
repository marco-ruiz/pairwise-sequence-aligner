import React, { useState, Fragment } from 'react';
import Paper from '@material-ui/core/Paper';
import Header from './Header';
import DescriptorForm from './DescriptorForm';
import ItemsDashboard from './ItemsDashboard';
import Solution, { solutionPresentations } from './Solution';
import { apiRequestPost } from '../api-requests';

const styles = {
    Paper: { 
        padding: 20, 
        marginTop: 10, 
        marginBottom: 10, 
        marginLeft: "auto",
        marginRight: "auto",
        maxWidth: "60rem"
    }, 
    PaperContainer: {
        margin: 15
    }
}

const presentations = solutionPresentations(value => value + "%");

export default ({ matrixes, alignUrl }) => {
    const [descriptor, setDescriptor] = useState({
        sequenceA: 'TNAKTAKVCQSFAWNEENTQKAVSMYQQLINENGLDFANSDGLKEIAKAVGAASPVSVRSKLTS',
        sequenceB: 'STVSPVFVCQSFAKNAGMYGERVGAVGAASPVSCFHLALTKQAQNKTIKPAVTSQLAKIIRSEVSNPPA',
        alignmentType: 'LOCAL',
        scoringMatrixName: 'BLOSUM100',
        fixGapPenalty: 5,
        varGapPenalty: 1,
        minScore: 20,
        maxNumberOfSolutions: 15,
    });
    const [solutions, setSolutions] = useState(undefined);

    const processApiResponse = ({ descriptor, solutions }) => {
        setDescriptor(descriptor);
        setSolutions(solutions);
    }

    return (!solutions || !solutions.length) ?
        <Fragment>
            <Header title="Pairwise Sequence Aligner" />
            <div style={styles.PaperContainer}>
                <Paper style={styles.Paper}>
                    <DescriptorForm
                        descriptor={descriptor}
                        matrixes={matrixes}
                        onSubmit={body => apiRequestPost(alignUrl, body, processApiResponse)}
                    />
                </Paper>
            </div>
        </Fragment>
        :
        <ItemsDashboard
            title="Pairwise Sequence Aligner"
            presentations={presentations}
            listItems={solutions}
            onRestart={() => setSolutions()}
            detailsFactory={item =>
                <Solution descriptor={descriptor} solution={item} />
            }
        />
}
