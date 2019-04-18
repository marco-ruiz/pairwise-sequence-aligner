import React, { Fragment } from 'react';
import Paper from '@material-ui/core/Paper';
import Header from './Header';
import DescriptorForm from './DescriptorForm';
import ItemsDashboard from './ItemsDashboard';
import Solution from './Solution';
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

const solutionFields = [
    { name: 'score', caption: 'Score' },
    { name: 'evalue', caption: 'E-Value', formatter: (value) => value.toExponential(1) },
    { name: 'positives', caption: 'Positives' },
    { name: 'positivesPercentage', caption: '% Positives', formatter: (value) => value + "%" },
    { name: 'identities', caption: 'Identities' },
    { name: 'identitiesPercentage', caption: '% Identities', formatter: (value) => value + "%" },
    { name: 'alignedSequences', caption: 'Length', formatter: (alignedSequences) => alignedSequences.length },
];

export default class AppPage extends React.Component {

    constructor(props) {
        super(props);
    
        this.state = {
            descriptor: {
                sequenceA: 'TNAKTAKVCQSFAWNEENTQKAVSMYQQLINENGLDFANSDGLKEIAKAVGAASPVSVRSKLTS',
                sequenceB: 'STVSPVFVCQSFAKNAGMYGERVGAVGAASPVSCFHLALTKQAQNKTIKPAVTSQLAKIIRSEVSNPPA',
                alignmentType: 'LOCAL',
                scoringMatrixName: 'BLOSUM100',
                fixGapPenalty: 5,
                varGapPenalty: 1,
                minScore: 20,
                maxNumberOfSolutions: 15,
            },
            matrixes: this.props.matrixes,
            solutions: []
        };
    }
    
    onRestart = () => {
        this.setState(() => ({ solutions: [] }));
    }

    processApiResponse = ({ descriptor, solutions}) => { 
        this.setState(() => ({ descriptor, solutions }));
    }

    render() {
        const definitionMode = (!this.state.solutions || !this.state.solutions.length);
        return definitionMode ? 
            <Fragment>
                <Header title="Pairwise Sequence Aligner" />
                <div style={styles.PaperContainer}>
                    <Paper style={styles.Paper}>
                        <DescriptorForm
                            descriptor={this.state.descriptor}
                            matrixes={this.state.matrixes}
                            onSubmit={body => apiRequestPost(this.props.alignUrl, body, this.processApiResponse)}
                        />
                    </Paper>
                </div>
            </Fragment>
            :
            <ItemsDashboard
                title="Pairwise Sequence Aligner" 
                fields={solutionFields}
                listItems={this.state.solutions}
                onRestart={this.onRestart}
                detailsFactory={(f, item) => 
                    <Solution descriptor={this.state.descriptor} fields={f} solution={item} />
                }
            />
    };
}
