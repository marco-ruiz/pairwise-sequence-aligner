import React, { Fragment } from 'react';
import Paper from '@material-ui/core/Paper';
import Header from './Header';
import DescriptorForm from './DescriptorForm';
import ItemsDashboard from './ItemsDashboard';
import Solution from './Solution';

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
    { name: 'formattedEValue', caption: 'E-Value' },
    { name: 'positives', caption: 'Positives' },
    { name: 'formattedPositivesPercentage', caption: '% Positives' },
    { name: 'identities', caption: 'Identities' },
    { name: 'formattedIdentitiesPercentage', caption: '% Identities' },
    { name: 'length', caption: 'Length' },
];

const postRequest = async (url, body, responseConsumer) => {
    const options = {
        method: 'POST',
        body: JSON.stringify(body),
        headers: {
            'Content-Type': 'application/json'
        }
    };

    const res = await fetch(url, options)
    const json = await res.json();
    responseConsumer(json);
}

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
            solutions: []
        };
    }
    
    onRestart = () => {
        this.setState(() => ({ solutions: [] }));
    }

    processApiResponse = ({ descriptor, solutions}) => { 
        solutions.forEach(sol => {
            sol.formattedEValue = sol.evalue.toExponential(1);
            sol.formattedPositivesPercentage = sol.positivesPercentage + "%";
            sol.formattedIdentitiesPercentage = sol.identitiesPercentage + "%";
            sol.length = sol.alignedSequences.length;
        });

        this.setState(() => ({ descriptor, solutions }));
    }

    render() {
        const definitionMode = (this.state.solutions === undefined || this.state.solutions.length === 0);
        return definitionMode ? 
            <Fragment>
                <Header title="Pairwise Sequence Aligner" />
                <div style={styles.PaperContainer}>
                    <Paper style={styles.Paper}>
                        <DescriptorForm
                            descriptor={this.state.descriptor}
                            onSubmit={body => postRequest(this.props.apiUrl, body, this.processApiResponse)}
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
