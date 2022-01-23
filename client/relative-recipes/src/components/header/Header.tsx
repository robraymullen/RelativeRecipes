import './header.css';

const Header = (): JSX.Element => {

    const title = "RelativeRecipes";

    return (
        <div className="header">
            <header>
                <h1>{title}</h1>
            </header>
        </div>
    )
};

export default Header;