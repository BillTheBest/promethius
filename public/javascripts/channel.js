var Channel = React.createClass({

        render: function() {
            return (
                <div data-channel-identifier={this.props.identifier} className='widget'>
                    <p>{this.props.title}</p>
                    <div className='value'>Awaiting Data</div>
                </div>
            );
        }
        });

